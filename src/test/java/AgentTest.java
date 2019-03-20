import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AgentTest extends TestCase {

    public AgentTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( AgentTest.class );
    }

    public void testNameAgent()
    {
        Agent agent = new Agent("AgentName", "AgentFirstName", "Guardien", "123", "AgentPassword", "./resources/agentPics.jpg");
        assertEquals(agent.getNom(), "AgentName");
    }

    public void testMatriculeAgent()
    {
        Agent agent = new Agent("AgentName", "AgentFirstName", "Guardien", "123", "AgentPassword", "./resources/agentPics.jpg");
        assertEquals(agent.getMatricule(), "123");
    }
    // TEST
}
