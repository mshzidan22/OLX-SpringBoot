package com.olx.util;

import com.olx.model.Location;
import lombok.Data;

@Data
public class LocationJson {
    private Long id;
    private String name;
    private Long parent;

    public Location toLocation(){
        Location l = new Location();
        l.setId(this.id);
        l.setName(this.name);
        l.setParent(this.parent);
        return l;
    }
}
