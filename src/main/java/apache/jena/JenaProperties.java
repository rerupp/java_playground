package apache.jena;

/**
 * Consolidate common properties into this silly class.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("WeakerAccess")
public class JenaProperties {

    /**
     * This property describes where the ontology file is located. My workstation is a Linux box and this is the
     * filesystem pathname to the ontology file. Java will normalize the path so on Windoz if the file is on the
     * D drive I could use d:/projects/ode/repos/ns-id-ontology/Core-ontology/core/Customer.owl and not need to
     * use the "\" path separator which is actually an escape character meaning I would need to use 2 for each one.
     */
    public static final String ONTOLOGY_FILENAME = "/projects/ode/repos/ns-id-ontology/Core-ontology/core/CoreIdentifier.owl";

    /**
     * This property describes what Fuseki server to use. I've checked it in with a URI specific to my Linux workstation.
     * I did this to make sure testing or the running of these samples didn't interfere with other users that may be doing
     * the same.
     */
    public static final String FUSEKI_SERVER = "http://localhost:9080/fuseki/example";

    // the development server we have available, make sure to create you own dataset and change "your-dataset" to match
//     public static final String FUSEKI_SERVER = "http://dep-dev-grph1.amers1e.cis.trcloud:3030/your-dataset";

}
