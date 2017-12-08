package proxy;

import dao.Dao;

public class DeleteProxy {
    public static void main(String[] args) {
        Dao.getInstance().deleteProxy();
    }
}
