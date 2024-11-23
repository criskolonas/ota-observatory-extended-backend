package gr.alexc.otaobservatory.dto.stats.confiscations;

public interface ConfiscationsByPrefecture {

    Long getPrefectureId();

    String getPrefectureName();

    Long getTotalConfiscations();
}
