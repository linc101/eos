
package util;

import java.util.List;

import play.db.jpa.GenericModel.JPAQuery;

public class PageOffset {
    int pn;

    int ps;

    int offset;

    public PageOffset(int pn, int ps) {
        super();
        pn = pn < 1 ? 1 : pn;
        ps = ps < 10 ? 10 : ps;

        this.pn = pn;
        this.ps = ps;
        this.offset = (pn - 1) * ps;
    }

    public PageOffset(int pn, int ps, int minPs) {

        pn = pn < 1 ? 1 : pn;
        ps = ps < minPs ? minPs : ps;

        this.pn = pn;
        this.ps = ps;
        this.offset = (pn - 1) * ps;
    }

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public static <T> List<T> appendQueryByPage(JPAQuery query, PageOffset po) {

        List<T> fetch = query.from(po.getOffset()).fetch(po.getPs());
        return fetch;
    }

    public static <T> List<T> appendQueryByPage(JPAQuery query, int offset, int ps) {

        List<T> fetch = query.from(offset).fetch(ps);
        return fetch;
    }
}
