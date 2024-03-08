package org.fugerit.java.core.db.daogen;

import java.math.BigDecimal;

public class IdFinderNG {

    private BigDecimal id;

    public BigDecimal getId() {
        return id;
    }

    public void setId( BigDecimal id ) {
        this.id = id;
    }

    public void setId( long id ) {
        this.setId( new BigDecimal( id ) );
    }

    @Override
    public String toString() {
        return "IdFinderNG [id=" + id + "]";
    }

}
