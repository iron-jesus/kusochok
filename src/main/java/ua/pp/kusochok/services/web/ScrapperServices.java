package ua.pp.kusochok.services.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScrapperServices {
    private final Map<String, IScrapper> servicesByTitleName = new HashMap<>();

    @Autowired
    public ScrapperServices(List<IScrapper> services){
        for (IScrapper service: services){
            register(service.getTitleName(), service);
        }
    }

    public void register(String titleName, IScrapper service) {
        this.servicesByTitleName.put(titleName, service);
    }

    public IScrapper getService(String titleName){
        return this.servicesByTitleName.get(titleName);
    }
}
