package inventarios.com.Sistema.Inventarios.Models;

public enum tableNames {
    USERINVENTORY{
        long idTable = 1000L;
        public long getIdTable(){return idTable;}
    },
    PRODUCT{
        long idTable = 2000L;
    public long getIdTable(){return idTable;}
    },
    CATEGORY{
        long idTable = 3000L;
    public long getIdTable(){return idTable;}
    },
    PARAMETER{
        long idTable = 4000L;
    public long getIdTable(){return idTable;}
    },
    LOGIN{
        long idTable = 5000L;
        public long getIdTable(){return idTable;}
    };

    public abstract long getIdTable();
}
