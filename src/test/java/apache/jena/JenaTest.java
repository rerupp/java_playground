package apache.jena;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.VCARD;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.jena.rdf.model.ResourceFactory.createPlainLiteral;
import static org.apache.jena.rdf.model.ResourceFactory.createResource;
import static org.apache.jena.rdf.model.ResourceFactory.createStatement;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test cases to demonstrate the utils and helpers.
 *
 * @author Rick Rupp
 */
@SuppressWarnings("JavaDoc")
public class JenaTest {

    private final Logger log = LoggerFactory.getLogger(JenaTest.class);

    @Test
    @Ignore
    public void testWorkflow() throws Exception {

        // load up the default graph with the ontology
        FusekiHelper.loadDefaultGraph(JenaProperties.FUSEKI_SERVER, JenaProperties.ONTOLOGY_FILENAME);
        final long ontologyCount = JenaUtils.getGraphTripleCount(JenaProperties.FUSEKI_SERVER);
        assertTrue("expected initial default ontology count to be > 0", 0 < ontologyCount);

        // load up a couple of customer graphs using the ontology (order must be sorted...)
        final String[] customerGraphs = new String[] { "http://data.onesourcetax.com/customer-1", "http://data.onesourcetax.com/customer-2" };
        for (final String customerGraph : customerGraphs) {
            FusekiHelper.loadNamedGraph(JenaProperties.FUSEKI_SERVER, customerGraph, JenaProperties.ONTOLOGY_FILENAME);
        }

        // get the named graphs
        final String[] graphURIs = JenaUtils.getNamedGraphs(JenaProperties.FUSEKI_SERVER);
        log.info("named graphs found {}", ArrayUtils.toString(graphURIs));
        for (int i = 0; i < customerGraphs.length; i++) {
            assertThat("Unexpected named graph...", graphURIs[i], is(customerGraphs[i]));
            final long tripleCount = JenaUtils.getGraphTripleCount(JenaProperties.FUSEKI_SERVER, graphURIs[i]);
            assertThat("Bad named graph " + graphURIs[i] + " size", tripleCount, is(ontologyCount));
        }

        // now delete the named graphs you created
        for (final String graphURI : graphURIs) {
            FusekiHelper.deleteNamedGraph(JenaProperties.FUSEKI_SERVER, graphURI);
        }

        // verify the named graphs are nada
        final String[] graphsPostDelete = JenaUtils.getNamedGraphs(JenaProperties.FUSEKI_SERVER);
        assertTrue("all named graphs were not deleted", 0 == graphsPostDelete.length);

        // delete the triples from the default graph
        FusekiHelper.deleteDefaultGraph(JenaProperties.FUSEKI_SERVER);

        // verify there are not triples in the default graph
        final long defaultGraphTripleCount = JenaUtils.getGraphTripleCount(JenaProperties.FUSEKI_SERVER);
        assertTrue("did not expect triples in the default graph", 0 == defaultGraphTripleCount);
    }

    @Test
    @Ignore
    public void testAddToGraph() {

        final String dataURI = "http://data.onesourcetax.com/example/";
        final String fullName = "Foo Bar";
        final String email = "fbar@mail.host";
        final Statement[] statements = new Statement[] {
                createStatement(createResource(dataURI + ":fullName-1"), VCARD.FN, createPlainLiteral(fullName)),
                createStatement(createResource(dataURI + ":email-1"), VCARD.EMAIL, createPlainLiteral(email))
        };

        final Model model = ModelFactory.createDefaultModel();
        model.add(statements);

        FusekiHelper.addToGraph(JenaProperties.FUSEKI_SERVER, dataURI, model);
        assertThat("Unexpected triple count for " + dataURI, JenaUtils.getGraphTripleCount(JenaProperties.FUSEKI_SERVER, dataURI), is(2L));

        // delete the triples from the default graph
        FusekiHelper.deleteNamedGraph(JenaProperties.FUSEKI_SERVER, dataURI);

        // verify there are not triples in the default graph
        assertTrue("did not expect triples in " + dataURI, 0 == JenaUtils.getGraphTripleCount(JenaProperties.FUSEKI_SERVER, dataURI));
    }

}