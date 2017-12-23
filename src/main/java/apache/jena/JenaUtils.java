package apache.jena;

import org.apache.commons.lang3.Validate;
import org.apache.jena.query.ParameterizedSparqlString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings({"JavaDoc", "WeakerAccess"})
public class JenaUtils {

    private static final Logger log = LoggerFactory.getLogger(JenaUtils.class);

    public static String[] getNamedGraphs(final String serviceURI) {
        final String sparqlQuery = "SELECT DISTINCT ?graph { GRAPH ?graph { ?s ?p ?o } } ORDER BY ?graph";
        final List<String> namedGraphs = new LinkedList<>();
        FusekiHelper.executeQuery(serviceURI, sparqlQuery, querySolution -> namedGraphs.add(querySolution.get("graph").toString()));
        return namedGraphs.toArray(new String[namedGraphs.size()]);
    }

    public static long getGraphTripleCount(final String serviceURI) {
        final String sparqlQuery = "SELECT (COUNT(*) AS ?count) WHERE { ?s ?p ?o }";
        final AtomicLong count = new AtomicLong(0);
        FusekiHelper.executeQuery(serviceURI, sparqlQuery, querySolution -> {
            count.set(querySolution.get("count").asLiteral().getLong());
        });
        return count.get();
    }

    public static long getGraphTripleCount(final String serviceURI, final String graphURI) {
        final String sparqlQuery = "SELECT (COUNT(*) AS ?count) FROM NAMED ?graphURI WHERE { graph ?g { ?s ?p ?o } }";
        final ParameterizedSparqlString sparql = new ParameterizedSparqlString(sparqlQuery);
        sparql.setIri("graphURI", Validate.notEmpty(graphURI, "The Graph name cannot be empty when getting a triple count..."));
        log.debug("Named graph triple count query: {}", sparql.asQuery());
        final AtomicLong count = new AtomicLong(0);
        FusekiHelper.executeQuery(serviceURI, sparql.toString(), querySolution -> {
            count.set(querySolution.get("count").asLiteral().getLong());
        });
        return count.get();
    }

}
