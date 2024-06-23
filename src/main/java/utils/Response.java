package utils;

import java.util.List;

public class Response {

        private boolean valid;
        private List<String> errors;
        private List<String> warnings;

        // This method might be what you're looking for
        public boolean isValid() {
            return valid;
        }

        public List<String> getErrors() {
            return errors;
        }

        public List<String> getWarnings() {
            return warnings;
        }
}
