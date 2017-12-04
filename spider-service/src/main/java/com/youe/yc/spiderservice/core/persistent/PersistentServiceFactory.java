package com.youe.yc.spiderservice.core.persistent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersistentServiceFactory {

    @Autowired(required = false)
    private List<IResultPersistentService> persistentServiceList;

    public List<IResultPersistentService> getPersistentServiceByDataType(String dataType) {
    	return persistentServiceList;
    	
       /* if(persistentServiceList==null) return null;
        for (IResultPersistentService resultPersistentService : persistentServiceList) {
            if (resultPersistentService.isSupportedByDataType(dataType))
                return resultPersistentService;
        }
        return null;*/
    }
}
