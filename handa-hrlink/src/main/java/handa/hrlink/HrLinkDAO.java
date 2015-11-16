package handa.hrlink;

import handa.beans.dto.DtrInput;

public interface HrLinkDAO
{
    String timeIn(DtrInput dtrInput);
    String timeOut(DtrInput dtrInput);
}