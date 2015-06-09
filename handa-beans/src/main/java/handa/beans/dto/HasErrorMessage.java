package handa.beans.dto;

import static com.google.common.base.MoreObjects.toStringHelper;

public class HasErrorMessage
{
    private String errorMessage;

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public boolean hasError()
    {
        return errorMessage != null;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("errorMessage", errorMessage)
                .toString();
    }
}