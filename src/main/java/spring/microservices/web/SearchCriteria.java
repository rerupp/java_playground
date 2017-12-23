package spring.microservices.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

@SuppressWarnings({"JavaDoc", "WeakerAccess"})
public class SearchCriteria {

    private String accountNumber;
    private String searchText;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(final String searchText) {
        this.searchText = searchText;
    }

    public boolean isValid() {
        return StringUtils.isNotEmpty(accountNumber) ? StringUtils.isEmpty(searchText) : StringUtils.isNotEmpty(searchText);
    }

    public boolean validate(final Errors errors) {
        if (StringUtils.isNotEmpty(accountNumber)) {
            if (accountNumber.length() != 9) {
                errors.rejectValue("accountNumber", "badFormat", "Account number should be 9 digits");
            } else {
                try {
                    //noinspection ResultOfMethodCallIgnored
                    Integer.parseInt(accountNumber);
                } catch (final NumberFormatException ignore) {
                    errors.rejectValue("accountNumber", "badFormat", "Account number should be 9 digits...");
                }
                if (StringUtils.isNotEmpty(searchText)) {
                    errors.rejectValue("searchText", "nonEmpty", "Cannot specify account number and search text together...");
                }
            }
        } else if (StringUtils.isEmpty(searchText)) {
            errors.rejectValue("searchCriteria", "empty", "Either account number or search text is required...");
        }
        return errors.hasErrors();
    }
}
