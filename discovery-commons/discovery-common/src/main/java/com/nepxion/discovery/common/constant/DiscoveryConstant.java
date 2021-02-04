package com.nepxion.discovery.common.constant;

/**
 * 服务发现常量
 */
public interface DiscoveryConstant {

    String DISCOVERY_VERSION = "6.6.0";

    String SPRING_APPLICATION_DISCOVERY_PLUGIN = "spring.application.discovery.plugin";
    String SPRING_APPLICATION_DISCOVERY_VERSION = "spring.application.discovery.version";
    String SPRING_APPLICATION_DISCOVERY_AGENT_VERSION = "spring.application.discovery.agent.version";
    String SPRING_APPLICATION_REGISTER_CONTROL_ENABLED = "spring.application.register.control.enabled";
    String SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED = "spring.application.discovery.control.enabled";
    String SPRING_APPLICATION_CONFIG_REST_CONTROL_ENABLED = "spring.application.config.rest.control.enabled";
    String SPRING_APPLICATION_SERVLET_WEB_SERVER_ENABLED = "spring.application.servlet.web.server.enabled";
    String SPRING_APPLICATION_REACTIVE_WEB_SERVER_ENABLED = "spring.application.reactive.web.server.enabled";
    String SPRING_APPLICATION_CONFIG_FORMAT = "spring.application.config.format";
    String SPRING_APPLICATION_CONFIG_PATH = "spring.application.config.path";
    String SPRING_APPLICATION_GROUP_KEY = "spring.application.group.key";
    String SPRING_APPLICATION_CONTEXT_PATH = "spring.application.context-path";
    String SPRING_APPLICATION_DEFAULT_PROPERTIES_PATH = "spring.application.default.properties.path";
    String SPRING_APPLICATION_DEFAULT_PROPERTIES_PATH_VALUE = "spring-application-default";
    String SPRING_APPLICATION_NO_SERVERS_RETRY_ENABLED = "spring.application.no.servers.retry.enabled";
    String SPRING_APPLICATION_NO_SERVERS_RETRY_TIMES = "spring.application.no.servers.retry.times";
    String SPRING_APPLICATION_NO_SERVERS_RETRY_AWAIT_TIME = "spring.application.no.servers.retry.await.time";
    String SPRING_APPLICATION_NO_SERVERS_NOTIFY_ENABLED = "spring.application.no.servers.notify.enabled";
    String SPRING_APPLICATION_PARAMETER_EVENT_ONSTART_ENABLED = "spring.application.parameter.event.onstart.enabled";

    String CONTEXT_PATH = "server.servlet.context-path";

    String ANNOTATION_CONFIG_SERVLET_WEB_SERVER_APPLICATION_CONTEXT = "AnnotationConfigServletWebServerApplicationContext";
    String ANNOTATION_CONFIG_REACTIVE_WEB_SERVER_APPLICATION_CONTEXT = "AnnotationConfigReactiveWebServerApplicationContext";

    String SPRING_APPLICATION_GROUP_GENERATOR_ENABLED = "spring.application.group.generator.enabled";
    String SPRING_APPLICATION_GROUP_GENERATOR_LENGTH = "spring.application.group.generator.length";
    String SPRING_APPLICATION_GROUP_GENERATOR_CHARACTER = "spring.application.group.generator.character";

    String SPRING_APPLICATION_GIT_GENERATOR_ENABLED = "spring.application.git.generator.enabled";
    String SPRING_APPLICATION_GIT_GENERATOR_PATH = "spring.application.git.generator.path";
    String SPRING_APPLICATION_GIT_VERSION_KEY = "spring.application.git.version.key";
    String GIT = "git";
    String GIT_COMMIT_ID = "git.commit.id";
    String GIT_COMMIT_ID_ABBREV = "git.commit.id.abbrev";
    String GIT_COMMIT_TIME = "git.commit.time";
    String GIT_BUILD_VERSION = "git.build.version";
    String GIT_TOTAL_COMMIT_COUNT = "git.total.commit.count";

    String APP_ID = "app.id";
    String SPRING_BOOT_VERSION = "spring.boot.version";
    String SPRING_APPLICATION_UUID = "spring.application.uuid";
    String SPRING_APPLICATION_NAME = "spring.application.name";
    String SPRING_APPLICATION_TYPE = "spring.application.type";
    String GROUP = "group";
    String SERVICE_ID = "serviceId";
    String HOST = "host";
    String PORT = "port";
    String METADATA = "metadata";

    String SERVICE = "service";
    String GATEWAY = "gateway";
    String CONSOLE = "console";
    String TEST = "test";

    String DYNAMIC_VERSION = "dynamic-version";
    String RULE = "rule";
    String DYNAMIC_RULE = "dynamic-rule";
    String DYNAMIC_GLOBAL_RULE = "dynamic-global-rule";
    String DYNAMIC_PARTIAL_RULE = "dynamic-partial-rule";
    String REACH_MAX_LIMITED_COUNT = "reach max limited count";
    String REGISTER_ISOLATION = "register isolation";

    String VERSION = "version";
    String REGION = "region";
    String ENVIRONMENT = "env";
    String ZONE = "zone";
    String ADDRESS = "address";
    String VERSION_WEIGHT = "version-weight";
    String REGION_WEIGHT = "region-weight";
    String ID_BLACKLIST = "id-blacklist";
    String ADDRESS_BLACKLIST = "address-blacklist";

    String N_D_PREFIX = "n-d-";
    String N_D_SERVICE_PREFIX = "n-d-service";

    String N_D_SERVICE_GROUP = "n-d-service-group";
    String N_D_SERVICE_TYPE = "n-d-service-type";
    String N_D_SERVICE_APP_ID = "n-d-service-app-id";
    String N_D_SERVICE_ID = "n-d-service-id";
    String N_D_SERVICE_ADDRESS = "n-d-service-address";
    String N_D_SERVICE_VERSION = "n-d-service-version";
    String N_D_SERVICE_REGION = "n-d-service-region";
    String N_D_SERVICE_ENVIRONMENT = "n-d-service-env";
    String N_D_SERVICE_ZONE = "n-d-service-zone";

    String N_D_VERSION = "n-d-version";
    String N_D_REGION = "n-d-region";
    String N_D_ENVIRONMENT = "n-d-env";
    String N_D_ADDRESS = "n-d-address";
    String N_D_VERSION_WEIGHT = "n-d-version-weight";
    String N_D_REGION_WEIGHT = "n-d-region-weight";
    String N_D_ID_BLACKLIST = "n-d-id-blacklist";
    String N_D_ADDRESS_BLACKLIST = "n-d-address-blacklist";

    String BLUE_GREEN = "blue-green";
    String BLUE_GREEN_BASIC = "blue-green-basic";
    String BLUE_BASIC = "blue-basic";

    String PORTAL = "portal";
    String BLUE = "blue";
    String GREEN = "green";
    String BASIC = "basic";
    String GRAY = "gray";
    String STABLE = "stable";
    String UNDEFINED = "undefined";

    String DOMAIN_GATEWAY = "domain-gateway";
    String NON_DOMAIN_GATEWAY = "non-domain-gateway";

    String BLACKLIST = "blacklist";
    String WHITELIST = "whitelist";

    String TRACE_ID = "trace-id";
    String SPAN_ID = "span-id";
    String SPAN_VALUE = "NEPXION";
    String SPAN_TAG_PLUGIN_NAME = "plugin";
    String SPAN_TAG_PLUGIN_VALUE = "Nepxion Discovery";

    String CLASS = "class";
    String METHOD = "method";
    String PARAMETER = "parameter";
    String RETURN = "return";
    String PARAMETER_MAP = "parameterMap";
    String EVENT = "event";
    String ERROR = "error";
    String ERROR_OBJECT = "error.object";

    String COOKIE = "Cookie";

    String XML_FORMAT = "xml";
    String JSON_FORMAT = "json";
    String PROPERTIES_FORMAT = "properties";
    String PREFIX_CLASSPATH = "classpath:";
    String PREFIX_FILE = "file:";

    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    String ENCODING_UTF_8 = "UTF-8";
    String ENCODING_GBK = "GBK";
    String ENCODING_ISO_8859_1 = "ISO-8859-1";
    String SEPARATE = ";";
    String EQUALS = "=";
    String DASH = "-";

    String ASYNC = "async";
    String SYNC = "sync";
    String GLOBAL = "global";
    String PARTIAL = "partial";
    String OK = "OK";
    String NO = "NO";
    String NA = "N/A";
    String DEFAULT = "default";
    String UNKNOWN = "unknown";
    String IGNORED = "ignored";

    String ENDPOINT_SCAN_PACKAGES = "com.nepxion.discovery.plugin.admincenter.endpoint";
    String INSPECTOR_ENDPOINT_CLASS_NAME = "com.nepxion.discovery.plugin.admincenter.endpoint.InspectorEndpoint";
    String INSPECTOR_ENDPOINT_METHOD_NAME = "inspect";
    String INSPECTOR_ENDPOINT_URL = "inspector/inspect";
    String INSPECTOR_ENDPOINT_HEADER = "endpoint-inspector-inspect";

    String EXPRESSION_PREFIX = "H";
    String EXPRESSION_REGEX = "\\#" + EXPRESSION_PREFIX + "\\['\\S+'\\]";
    String EXPRESSION_SUB_PREFIX = "#" + EXPRESSION_PREFIX + "['";
    String EXPRESSION_SUB_SUFFIX = "']";

    String DEFAULT_XML_RULE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
            "<rule>\r\n" +
            "</rule>";
    String DEFAULT_JSON_RULE = "{}";
}