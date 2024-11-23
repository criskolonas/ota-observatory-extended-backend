package gr.alexc.otaobservatory.exception;

import gr.alexc.otaobservatory.enums.OTAVariable;

public class VariableDataNotAvailable extends RuntimeException {
    public VariableDataNotAvailable(OTAVariable variable) {
        super("data for variable " + variable + " not available");
    }
}
