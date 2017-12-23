package apache.jena;

import org.apache.commons.lang3.Validate;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;

import java.util.function.Consumer;

/**
 * A collection of methods helpers to interact with the Fuseki server.
 *
 * @author Rick Rupp
 */
@SuppressWarnings({"JavaDoc", "WeakerAccess"})
public class FusekiHelper {

    public static void loadDefaultGraph(final String serviceURI, final String modelSource) {
        // this will replace the default graph on the Fuseki server
        final Model model = loadModel(modelSource);
        createDatasetAccessor(serviceURI).putModel(model);
    }

    public static void loadNamedGraph(final String serviceURI, final String graphURI, final String modelSource) {
        // this will replace the named graph on the Fuseki server
        final Model model = loadModel(modelSource);
        createDatasetAccessor(serviceURI).putModel(Validate.notEmpty(graphURI, "The Graph URI for load cannot be empty..."), model);
    }

    public static void addToGraph(final String serviceURI, final String graphURI, final Model model) {
        // this will replace the named graph on the Fuseki server
        createDatasetAccessor(serviceURI).add(Validate.notEmpty(graphURI, "The Graph URI for load cannot be empty..."), model);
    }

    public static void deleteDefaultGraph(final String serviceURI) {
        createDatasetAccessor(serviceURI).deleteDefault();
    }

    public static void deleteNamedGraph(final String serviceURI, final String graphURI) {
        createDatasetAccessor(serviceURI).deleteModel(Validate.notEmpty(graphURI, "The Graph URI for delete cannot be empty..."));
    }

    public static void executeQuery(final String serviceURI, final String sparqlQuery, final Consumer<QuerySolution> consumer) {
        try (final QueryExecution queryExecution = QueryExecutionFactory.sparqlService(serviceURI, sparqlQuery)) {
            // use Java 8 streams and lambda to process the query results
            queryExecution.execSelect().forEachRemaining(consumer);
        }
    }

    private static Model loadModel(final String modelSource) {
        // the FileManager will cache input and understands either file or URI
        return FileManager.get().loadModel(Validate.notEmpty(modelSource, "The Model source cannot be empty..."));
    }

    private static DatasetAccessor createDatasetAccessor(final String fusekiServerURI) {
        // the DatasetAccessor is not guaranteed to be thread safe
        return DatasetAccessorFactory.createHTTP(Validate.notEmpty(fusekiServerURI, "The Fuseki server URI cannot be empty..."));
    }
}
