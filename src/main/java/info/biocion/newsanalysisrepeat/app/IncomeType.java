package info.biocion.newsanalysisrepeat.app;

public enum IncomeType {


    WEEKRATE("周收益率"),
    TOTALRATE("总收益率"),
    TOTALAMOUNT("累计盈利");
    ;
    private final String type ;


    private IncomeType(String type)
    {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
