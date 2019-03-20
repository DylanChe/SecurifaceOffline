import java.sql.*;
import java.util.ArrayList;

/**
 * Classe permettant la connexion à la base de donnée
 * Et la manipulation des différents agents.
 */

// TEST

public class Agent {

    private String nom;
    private String prenom;
    private String poste;
    private String matricule;
    private String password;
    private String chemin_photo;

    public Agent(String nom, String prenom, String poste, String matricule, String password, String chemin_photo){
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.matricule = matricule;
        this.password = password;
        this.chemin_photo = chemin_photo;
    }

    public static ArrayList getListAgents() {

        ArrayList<Agent> agents = new ArrayList();
        try {
            Connection connection = ConnectBDD.getConnection();
            /* Création de l'objet gérant les requêtes */
            Statement statement = connection.createStatement();

            /* Exécution d'une requête de lecture */
            ResultSet resultat = statement.executeQuery( "SELECT nom, prenom, poste, matricule, password, chemin_photo FROM agents;" );


            /* Récupération des données du résultat de la requête de lecture */
            while ( resultat.next() ) {
                String nomAgent = resultat.getString( "nom" );
                String prenomAgent = resultat.getString( "prenom" );
                String posteAgent = resultat.getString( "poste" );
                String matriculeAgent = resultat.getString( "matricule" );
                String passwordAgent = resultat.getString( "password" );
                String cheminPhotoAgent = resultat.getString( "chemin_photo" );
                Agent agent = new Agent(nomAgent, prenomAgent, matriculeAgent, posteAgent, passwordAgent, cheminPhotoAgent);
                agents.add(agent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agents;
    }

    public static void putAgent(String nom, String prenom, String poste, String matricule, String password, String chemin_photo){

        try {

            Connection connection = ConnectBDD.getConnection();

            /* Création de l'objet gérant les requêtes */
            Statement statement = connection.createStatement();

            String INSERT_QUERY = "INSERT INTO agents (nom, prenom, poste, matricule, password, chemin_photo) VALUES (?,?,?,?,?,?)";

            /* Exécution d'une requête d'écriture */
            PreparedStatement st = connection.prepareStatement( INSERT_QUERY, Statement.RETURN_GENERATED_KEYS );
            st.setString( 1, nom );
            st.setString( 2, prenom );
            st.setString( 3, poste );
            st.setString( 4, matricule );
            st.setString( 5, password );
            st.setString( 6, chemin_photo );
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeAgent(Integer agentMatricule){

        try {

            Connection connection = ConnectBDD.getConnection();

            /* Création de l'objet gérant les requêtes */
            Statement statement = connection.createStatement();

            String DELETE_QUERY = "DELETE FROM agents WHERE matricule = '"+agentMatricule+"'";

            /* Exécution d'une requête de suppression */
            PreparedStatement st = connection.prepareStatement( DELETE_QUERY, Statement.RETURN_GENERATED_KEYS );
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Agent getAgent(Integer agentMatricule) {

        try {
            Connection connection = ConnectBDD.getConnection();
            /* Création de l'objet gérant les requêtes */
            Statement statement = connection.createStatement();
            ResultSet resultat = statement.executeQuery("SELECT nom, prenom, poste, matricule, password, chemin_photo FROM agents WHERE matricule =" + agentMatricule + ";");
            resultat.next();
            String nomAgent = resultat.getString("nom");
            String prenomAgent = resultat.getString("prenom");
            String posteAgent = resultat.getString("poste");
            String matriculeAgent = resultat.getString("matricule");
            String passwordAgent = resultat.getString("password");
            String cheminPhotoAgent = resultat.getString("chemin_photo");
            return new Agent(nomAgent, prenomAgent, posteAgent, matriculeAgent, passwordAgent, cheminPhotoAgent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChemin_photo() {
        return chemin_photo;
    }

    public void setChemin_photo(String chemin_photo) {
        this.chemin_photo = chemin_photo;
    }

    public static boolean isExisting (Integer agentMatricule){

        Boolean verif = false;
        try {
            Connection connection = ConnectBDD.getConnection();
            /* Création de l'objet gérant les requêtes */
            Statement statement = connection.createStatement();

            /* Exécution d'une requête de lecture */
            ResultSet resultat = statement.executeQuery( "SELECT nom FROM agents WHERE matricule = '"+agentMatricule+"';" );

            if (resultat.next()) {
                /* Traiter ici les valeurs récupérées. */
                verif = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return verif;
    }

    public static boolean getReservation (Integer agentMatricule, String materielName){

        Boolean verif = false;
        try {
            Connection connection = ConnectBDD.getConnection();
            /* Création de l'objet gérant les requêtes */
            Statement statement = connection.createStatement();

            String agentName = getAgent(agentMatricule).getNom();

            /* Exécution d'une requête de lecture */
            ResultSet resultat = statement.executeQuery( "SELECT nomAgent, nomMateriel FROM reservation WHERE nomAgent = '"+agentName+"' AND nomMateriel = '"+materielName+"';" );

            if (resultat.next()) {
                /* Traiter ici les valeurs récupérées. */
                verif = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return verif;
    }

    public static void putReservation(Integer agentMatricule, String materielName){

        try {

            Connection connection = ConnectBDD.getConnection();

            /* Création de l'objet gérant les requêtes */
            Statement statement = connection.createStatement();

            String agentName = getAgent(agentMatricule).getNom();

            String INSERT_QUERY = "INSERT INTO reservation (nomAgent, nomMateriel) VALUES (?,?)";

            /* Exécution d'une requête d'écriture */
            PreparedStatement st = connection.prepareStatement( INSERT_QUERY, Statement.RETURN_GENERATED_KEYS );
            st.setString( 1, agentName );
            st.setString( 2, materielName );
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void removeReservation(Integer agentMatricule, String materielName){

        try {

            Connection connection = ConnectBDD.getConnection();

            /* Création de l'objet gérant les requêtes */
            Statement statement = connection.createStatement();

            String agentName = getAgent(agentMatricule).getNom();

            String DELETE_QUERY = "DELETE FROM reservation WHERE nomAgent = `"+agentName+"` AND nomMateriel = `"+materielName+"`";

            /* Exécution d'une requête d'écriture */
            PreparedStatement st = connection.prepareStatement(DELETE_QUERY);
            st.setString( 1, agentName );
            st.setString( 2, materielName );
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Agent{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", poste='" + poste + '\'' +
                ", matricule='" + matricule + '\'' +
                ", password='" + password + '\'' +
                ", chemin_photo='" + chemin_photo + '\'' +
                '}';
    }
}