package hexlet.code.differenceDTO;

public class DifferenceDto {

    public String param;
    public Object firstValue;
    public Object secondValue;

    public DifferenceDto(String param, Object value1, Object value2) {
        if (param == null) {
            throw new IllegalArgumentException("Parameter name is null");
        }

        this.param = param;
        this.firstValue = value1;
        this.secondValue = value2;
    }


}
