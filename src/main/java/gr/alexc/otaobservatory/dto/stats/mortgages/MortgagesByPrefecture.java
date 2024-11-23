package gr.alexc.otaobservatory.dto.stats.mortgages;

public interface MortgagesByPrefecture {
    Long getPrefectureId();

    String getPrefectureName();

    Long getTotalMortgages();
}
