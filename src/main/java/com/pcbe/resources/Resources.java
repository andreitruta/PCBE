package com.pcbe.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Resources {
    private List<ResourceType> resourceList;

    public List<ResourceType> getResourceList() {
        return resourceList;
    }

    @XmlElement(name = "resource")
    public void setResourceList(List<ResourceType> resourceList) {
        this.resourceList = resourceList;
    }
}
