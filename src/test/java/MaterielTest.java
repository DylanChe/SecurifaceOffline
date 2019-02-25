import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MaterielTest extends TestCase {

    public MaterielTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( AgentTest.class );
    }

    public void testNameMateriel()
    {
        Materiel materiel = new Materiel("MaterielName", "10-09-2019", "11-09-2019", "MaterielDescription", 10);
        assertEquals(materiel.getNom(), "MaterielName");
    }

    public void testQuantiteMateriel()
    {
        Materiel materiel = new Materiel("MaterielName", "10-09-2019", "11-09-2019", "MaterielDescription", 10);
assertEquals(materiel.getQuantite(), "10");
    }
}
