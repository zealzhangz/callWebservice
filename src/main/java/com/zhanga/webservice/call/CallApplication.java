package com.zhanga.webservice.call;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CallApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CallApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String scope = "all";
        String wsdlUrl = "";

        Object[] objects = null;
        if ("prod".equals(args[0])) {
            wsdlUrl = "http://10.1.22.5:31980/contract/services/NoticeWebServiceForSap?wsdl";
        } else if ("test".equals(args[0])) {
            wsdlUrl = "http://10.1.1.176:8082/services/NoticeWebServiceForSap?wsdl";
        }
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client webserviceClient = dcf.createClient(wsdlUrl);
            objects = webserviceClient.invoke("syncSuccessNotice", scope);
            log.info("调用结果：" + objects[0]);
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
