package org.example.constants;

public class DatabasePaths {
    public static final String DATABASE_FOLDER = "data/";// dossier principal ou toutes les donnees sont stockees


    public static final String MODELS_FOLDER = DATABASE_FOLDER + "store_db/"; // un sous dossier dans data/

    public static final String PRODUCT_FILE = MODELS_FOLDER + "products.db";//path complet vers le fichier ou les produits seront stockes

    // public static final String CLIENT_FILE = MODELS_FOLDER + "clients.db";
    //public static final String ORDER_FILE = MODELS_FOLDER + "orders.db";
}
