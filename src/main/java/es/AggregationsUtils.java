package es;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;

/**
 * @author peramdy on 2018/9/26.
 */
public class AggregationsUtils {

    private static final String ES_KEYWORD_SUFFIX = ".keyword";
    private static final String ES_CARDINALITY_NAME = "uniqueount";
    private static final String ES_TERMS_NAME = "termsName";


    /**
     * queryAggregationTermsCardinality
     *
     * @param addresses          es 地址
     * @param indexName
     * @param cardinalityContent
     * @param termFields
     * @param field
     * @param values
     * @return
     * @throws IOException
     */
    public static JestResult queryAggregationTermsCardinality(List<String> addresses, String indexName, String cardinalityContent,
                                                              List<String> termFields, String field, String... values) throws IOException {
        JestClient jestClient = null;
        try {
            jestClient = EsUtils.getClient(addresses);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            QueryBuilder queryBuilder = QueryBuilders.termsQuery(field, values);
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(queryBuilder);
            TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms(ES_TERMS_NAME);
            termFields.stream().forEach(termFiled -> termsAggregationBuilder.field(termFiled + ES_KEYWORD_SUFFIX));
            AggregationBuilder aggregationBuilder = termsAggregationBuilder.subAggregation(
                    AggregationBuilders
                            .cardinality(ES_CARDINALITY_NAME)
                            .field(cardinalityContent + ES_KEYWORD_SUFFIX)
            );
            searchSourceBuilder.query(boolQueryBuilder);
            searchSourceBuilder.aggregation(aggregationBuilder);
            searchSourceBuilder.size(0);
            Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(indexName).build();
            JestResult result = jestClient.execute(search);

            return result;
        } finally {
            if (jestClient != null) {
                jestClient.close();
            }
        }
    }


    /**
     * queryAggregationTermsCardinality
     *
     * @param address
     * @param port
     * @param indexName
     * @param cardinalityContent
     * @param termFields
     * @param field
     * @param values
     * @return
     * @throws IOException
     */
    public static JestResult queryAggregationTermsCardinality(String address, Integer port, String indexName, String cardinalityContent,
                                                              List<String> termFields, String field, String... values) throws IOException {
        List<String> addresses = EsUtils.parseAddress(address, port);
        return queryAggregationTermsCardinality(addresses, indexName, cardinalityContent, termFields, field, values);
    }


    public static void main(String[] args) throws IOException {
//        queryAggregationTermsCardinality("http://192.168.10.241", 9200, "logstash-senselovers*", "extend.device_id", new String[]{"105"});

    }

}
