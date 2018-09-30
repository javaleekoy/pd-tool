package es;

import com.alibaba.fastjson.JSONObject;
import exception.PdException;
import exception.PdIOException;
import exception.PdNullPointerException;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.cluster.Health;
import io.searchbox.cluster.NodesInfo;
import io.searchbox.core.*;
import io.searchbox.indices.*;
import io.searchbox.indices.mapping.GetMapping;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @author peramdy on 2018/9/18.
 * @descption 索引（index）相当于数据库，
 * 类型(type)相当于数据表，
 * 映射(Mapping)相当于数据表的表结构。
 * ElasticSearch中的映射（Mapping）用来定义一个文档，可以定义所包含的字段以及字段的类型、分词器及属性等等。
 */
public class EsUtils {


    private final static String ES_MAPPINGS = "mappings";
    private final static String ES_TYPE_PROPERTIES = "properties";
    private final static String ES_DEFULT_ADDRESS = "http://localhost:9200";


    /**
     * create JestClient
     *
     * @param addresses http://0.0.0.0:00
     * @return JestClient
     */
    public static JestClient getClient(List<String> addresses) {
        if (addresses == null || addresses.size() == 0) {
            if (addresses == null) {
                addresses = new ArrayList<>();
            }
            addresses.add(ES_DEFULT_ADDRESS);
        }
        JestClientFactory jestClientFactory = new JestClientFactory();
        HttpClientConfig clientConfig = new HttpClientConfig
                .Builder(addresses)
                .maxTotalConnection(50)
                .multiThreaded(true)
                .readTimeout(5000)
                .build();
        jestClientFactory.setHttpClientConfig(clientConfig);
        JestClient jestClient = jestClientFactory.getObject();
        return jestClient;
    }


    /**
     * close JestClient
     *
     * @param jestClient JestClient
     */
    private static void closeClient(JestClient jestClient) {
        if (jestClient != null) {
            try {
                jestClient.close();
            } catch (IOException e) {
                throw new PdIOException(e);
            }
        }
    }


    /**
     * delete index
     *
     * @param index     index
     * @param addresses http://0.0.0.0:00
     * @return JestResult
     */
    public static JestResult delIndex(List<String> addresses, String index) {
        JestClient jestClient = getClient(addresses);
        DeleteIndex deleteIndex = new DeleteIndex.Builder(index).build();
        JestResult result;
        try {
            result = jestClient.execute(deleteIndex);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * delete index
     *
     * @param address
     * @param port
     * @param index
     * @return
     */
    public static JestResult delIndex(String address, Integer port, String index) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return delIndex(addresses, index);
    }


    /**
     * clear cache
     *
     * @param addresses
     * @return JestResult
     */
    public static JestResult clearCache(List<String> addresses) {
        JestClient jestClient = getClient(addresses);
        ClearCache clearCache = new ClearCache.Builder().build();
        JestResult result;
        try {
            result = jestClient.execute(clearCache);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }


    /**
     * @param address
     * @param port
     * @return
     */
    public static JestResult clearCache(String address, Integer port) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return clearCache(addresses);
    }

    /**
     * close index
     *
     * @param index
     * @param addresses
     * @return
     */
    public static JestResult closeIndex(List<String> addresses, String index) {
        JestClient jestClient = getClient(addresses);
        CloseIndex closeIndex = new CloseIndex.Builder(index).build();
        JestResult result;
        try {
            result = jestClient.execute(closeIndex);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * closeIndex
     *
     * @param address
     * @param port
     * @param index
     * @return
     */
    public static JestResult closeIndex(String address, Integer port, String index) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return closeIndex(addresses, index);
    }

    /**
     * optimize Index
     *
     * @param addresses
     * @return
     */
    public static JestResult optimizeIndex(List<String> addresses) {
        JestClient jestClient = getClient(addresses);
        Optimize optimize = new Optimize.Builder().build();
        JestResult result;
        try {
            result = jestClient.execute(optimize);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * @param address
     * @param port
     * @return
     */
    public static JestResult optimizeIndex(String address, Integer port) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return optimizeIndex(addresses);
    }

    /**
     * flushIndex
     *
     * @param addresses
     * @return
     */
    public static JestResult flushIndex(List<String> addresses) {
        JestClient jestClient = getClient(addresses);
        Flush flush = new Flush.Builder().build();
        JestResult result;
        try {
            result = jestClient.execute(flush);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * flushIndex
     *
     * @param address
     * @param port
     * @return
     */
    public static JestResult flushIndex(String address, Integer port) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return flushIndex(addresses);
    }

    /**
     * indicesExists
     *
     * @param addresses
     * @param indices
     * @return
     */
    public static JestResult indicesExists(List<String> addresses, Collection<String> indices) {
        JestClient jestClient = getClient(addresses);
        IndicesExists indicesExists = new IndicesExists.Builder(indices).build();
        JestResult result;
        try {
            result = jestClient.execute(indicesExists);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * indicesExists
     *
     * @param address
     * @param port
     * @param indices
     * @return
     */
    public static JestResult indicesExists(String address, Integer port, Collection<String> indices) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return indicesExists(addresses, indices);
    }


    /**
     * nodesInfo
     *
     * @param addresses
     * @return
     */
    public static JestResult nodesInfo(List<String> addresses) {
        JestClient jestClient = getClient(addresses);
        NodesInfo nodesInfo = new NodesInfo.Builder().build();
        JestResult result;
        try {
            result = jestClient.execute(nodesInfo);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * nodesInfo
     *
     * @param address
     * @param port
     * @return
     */
    public static JestResult nodesInfo(String address, Integer port) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return nodesInfo(addresses);
    }

    /**
     * health
     *
     * @param addresses
     * @return
     */
    public static JestResult health(List<String> addresses) {
        JestClient jestClient = getClient(addresses);
        Health health = new Health.Builder().build();
        JestResult result;
        try {
            result = jestClient.execute(health);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * health
     *
     * @param address
     * @param port
     * @return
     */
    public static JestResult health(String address, Integer port) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return health(addresses);
    }


    /**
     * updateDocument
     *
     * @param addresses
     * @param scraddresst
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static JestResult updateDocument(List<String> addresses, String scraddresst,
                                            String index, String type, String id) {
        JestClient jestClient = getClient(addresses);
        Update update = new Update.Builder(scraddresst).index(index).type(type).id(id).build();
        JestResult result = null;
        try {
            result = jestClient.execute(update);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * updateDocument
     *
     * @param address
     * @param port
     * @param scraddresst
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static JestResult updateDocument(String address, Integer port, String scraddresst,
                                            String index, String type, String id) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return updateDocument(addresses, scraddresst, index, type, id);
    }

    /**
     * delDocument
     *
     * @param addresses
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static JestResult delDocument(List<String> addresses, String index, String type, String id) {
        JestClient jestClient = getClient(addresses);
        Delete delete = new Delete.Builder(id).index(index).type(type).build();
        JestResult result;
        try {
            result = jestClient.execute(delete);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * delDocument
     *
     * @param address
     * @param port
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static JestResult delDocument(String address, Integer port, String index, String type, String id) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return delDocument(addresses, index, type, id);
    }


    /**
     * delDocumentByQuery
     *
     * @param addresses
     * @param params
     * @param indices
     * @param types
     * @return
     */
    public static JestResult delDocumentByQuery(List<String> addresses, String params,
                                                Collection<String> indices, Collection<String> types) {
        JestClient jestClient = getClient(addresses);
        DeleteByQuery deleteByQuery = new DeleteByQuery.Builder(params).addIndices(indices).addTypes(types).build();
        JestResult result;
        try {
            result = jestClient.execute(deleteByQuery);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * delDocumentByQuery
     *
     * @param address
     * @param port
     * @param params
     * @param indices
     * @param types
     * @return
     */
    public static JestResult delDocumentByQuery(String address, Integer port, String params,
                                                Collection<String> indices, Collection<String> types) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return delDocumentByQuery(addresses, params, indices, types);
    }


    /**
     * getDocument
     *
     * @param addresses
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static JestResult getDocument(List<String> addresses, String index, String type, String id) {
        JestClient jestClient = getClient(addresses);
        Get get = new Get.Builder(index, id).type(type).build();
        JestResult result;
        try {
            result = jestClient.execute(get);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * getDocument
     *
     * @param address
     * @param port
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static JestResult getDocument(String address, Integer port, String index, String type, String id) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return getDocument(addresses, index, type, id);
    }


    /**
     * suggest
     *
     * @param addresses
     * @param suggestContent
     * @return
     */
    public static SuggestResult suggest(List<String> addresses, String suggestContent) {
        JestClient jestClient = getClient(addresses);
        Suggest suggest = new Suggest.Builder(suggestContent).build();
        SuggestResult result;
        try {
            result = jestClient.execute(suggest);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * suggest
     *
     * @param address
     * @param port
     * @param suggestContent
     * @return
     */
    public static JestResult suggest(String address, Integer port, String suggestContent) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return suggest(addresses, suggestContent);
    }


    /**
     * bulkIndex
     *
     * @param addresses
     * @param index
     * @param type
     * @param t
     * @param <T>
     * @return
     */
    public static <T> JestResult bulkIndex(List<String> addresses, String index, String type, T t) {
        Bulk bulk = new Bulk.Builder()
                .defaultIndex(index)
                .defaultType(type)
                .addAction(Arrays.asList(new Index.Builder(t).build()))
                .build();

        JestClient jestClient = getClient(addresses);
        JestResult result;
        try {
            result = jestClient.execute(bulk);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * bulkIndex
     *
     * @param address
     * @param port
     * @param index
     * @param type
     * @param t
     * @param <T>
     * @return
     */
    public static <T> JestResult bulkIndex(String address, Integer port, String index, String type, T t) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return bulkIndex(addresses, index, type, t);
    }

    /**
     * createIndex
     *
     * @param addresses
     * @param t
     * @param indexName
     * @param typeName
     * @param <T>
     * @return
     */
    public static <T> JestResult createIndex(List<String> addresses, T t, String indexName, String typeName) {
        Index index = new Index.Builder(t).index(indexName).type(typeName).build();
        JestClient jestClient = getClient(addresses);
        JestResult result;
        try {
            result = jestClient.execute(index);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * createIndex
     *
     * @param address
     * @param port
     * @param t
     * @param indexName
     * @param typeName
     * @param <T>
     * @return
     */
    public static <T> JestResult createIndex(String address, Integer port, T t, String indexName, String typeName) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return createIndex(addresses, t, indexName, typeName);
    }

    /**
     * searchEvent
     *
     * @param addresses
     * @param param
     * @param indexName
     * @param indexType
     * @return
     */
    public static JestResult searchEvent(List<String> addresses, String param, String indexName, String indexType) {
        JSONObject returnData = JSONObject.parseObject(param);
        Search search = new Search.Builder(returnData.toString()).addType(indexType).addIndex(indexName).build();
        JestClient jestClient = getClient(addresses);
        SearchResult result;
        try {
            result = jestClient.execute(search);
        } catch (IOException e) {
            throw new PdIOException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * searchEvent
     *
     * @param address
     * @param port
     * @param param
     * @param indexName
     * @param indexType
     * @return
     */
    public static JestResult searchEvent(String address, Integer port, String param, String indexName, String indexType) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return searchEvent(addresses, param, indexName, indexType);
    }

    /**
     * searchAll
     *
     * @param addresses
     * @param indexName
     * @return
     */
    public static SearchResult searchAll(List<String> addresses, String indexName, int pageNo, int pageSize) {
        JestClient jestClient = getClient(addresses);
        SearchResult result;
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            pageNo = pageNo > 0 ? pageNo - 1 : 0;
            pageSize = pageSize > 0 ? pageSize : 10;
            searchSourceBuilder.size(pageSize);
            searchSourceBuilder.from(pageNo * pageSize);
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(indexName).build();
            result = jestClient.execute(search);
        } catch (Exception e) {
            throw new PdException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }


    /**
     * searchAll
     *
     * @param address
     * @param port
     * @param indexName
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static JestResult searchAll(String address, Integer port, String indexName, int pageNo, int pageSize) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return searchAll(addresses, indexName, pageNo, pageSize);
    }

    /**
     * searchByString
     *
     * @param addresses
     * @param query
     * @param indexName
     * @param fields
     * @return
     */
    public static SearchResult searchByString(List<String> addresses, String query, String indexName, String... fields) {
        JestClient jestClient = getClient(addresses);
        SearchResult result;
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            for (String field : fields) {
                highlightBuilder.field(field);
            }
            highlightBuilder.preTags("<em>");
            highlightBuilder.postTags("</em>");
            searchSourceBuilder.highlighter(highlightBuilder);
            Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(indexName).build();
            result = jestClient.execute(search);
        } catch (Exception e) {
            throw new PdException(e);
        } finally {
            closeClient(jestClient);
        }
        return result;
    }

    /**
     * searchByString
     *
     * @param address
     * @param port
     * @param query
     * @param indexName
     * @param fields
     * @return
     */
    public static JestResult searchByString(String address, Integer port, String query, String indexName, String... fields) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return searchByString(addresses, query, indexName, fields);
    }

    /**
     * fieldNames
     *
     * @param addresses
     * @param indexName
     * @param typeNames
     * @return
     */
    public static List<String> fieldNames(List<String> addresses, String indexName, Set<String> typeNames) {
        JestClient jestClient = getClient(addresses);
        if (StringUtils.isBlank(indexName)) {
            throw new PdNullPointerException("indexName is null !");
        }
        GetMapping.Builder getMappingBuilder = new GetMapping.Builder().addIndex(indexName);
        if (typeNames != null && typeNames.size() > 0) {
            getMappingBuilder.addTypes(typeNames);
        }
        GetMapping getMapping = getMappingBuilder.build();
        try {
            JestResult result = jestClient.execute(getMapping);
            String resultJsonString = result.getJsonString();
            if (StringUtils.isBlank(resultJsonString)) {
                return null;
            }
            JSONObject jsonObject = JSONObject.parseObject(resultJsonString);
            JSONObject indexJson = jsonObject.getJSONObject(indexName);
            if (indexJson == null || StringUtils.isBlank(indexJson.toString())) {
                return null;
            }
            JSONObject typesJson = indexJson.getJSONObject(ES_MAPPINGS);
            if (typesJson == null || StringUtils.isBlank(typesJson.toString())) {
                return null;
            }
            if (typeNames == null || typeNames.size() == 0) {
                typeNames = typesJson.keySet();
            }
            if (typeNames == null || typeNames.size() == 0) {
                return null;
            }
            List<String> keys = new ArrayList<>();
            typeNames.stream().forEach(type -> {
                JSONObject firstPropertiesJson = typesJson.getJSONObject(type).getJSONObject(ES_TYPE_PROPERTIES);
                Set<String> firstKeys = firstPropertiesJson.keySet();
                firstKeys.stream().forEach(firstKey -> {
                    if (StringUtils.isNotBlank(firstKey)) {
                        JSONObject secondPropertiesJson = firstPropertiesJson.getJSONObject(firstKey).getJSONObject(ES_TYPE_PROPERTIES);
                        if (secondPropertiesJson != null && StringUtils.isNotBlank(secondPropertiesJson.toString())) {
                            Set<String> secondKeys = secondPropertiesJson.keySet();
                            secondKeys.stream().forEach(secondKey -> {
                                String secondKeyName = firstKey + "." + secondKey;
                                if (!keys.contains(secondKeyName)) {
                                    keys.add(secondKeyName);
                                }
                            });
                        } else {
                            if (!keys.contains(firstKey)) {
                                keys.add(firstKey);
                            }
                        }
                    }
                });
            });
            keys.sort((String o1, String o2) -> {
                if (o1.compareToIgnoreCase(o2) < 0) {
                    return -1;
                }
                return 1;
            });
            /*keys.stream().forEach(str -> System.out.println(str));*/
            return keys;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<String> fieldNames(String address, Integer port, String indexName, Set<String> typeNames) {
        parseAddress(address, port);
        List<String> addresses = new ArrayList<>();
        addresses.add(address + ":" + port);
        return fieldNames(addresses, indexName, typeNames);
    }


    public static List<String> parseAddress(String address, Integer port) {
        List<String> addresses = new ArrayList<>();
        if (StringUtils.isBlank(address)) {
            address = "http://localhost";
        }
        if (port == null) {
            port = 9200;
        }
        addresses.add(address + ":" + port);
        return addresses;
    }


    public static void main(String[] args) throws UnknownHostException {
        String indexName = "logstash-sensenow-2018.08";
        Set<String> typeNames = new HashSet<>();
        typeNames.add("_default_");
        fieldNames("http://192.168.10.241", 9200, indexName, typeNames).stream().forEach(s -> System.out.println(s));
    }


}
