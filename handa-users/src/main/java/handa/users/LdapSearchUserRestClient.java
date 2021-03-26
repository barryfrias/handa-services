package handa.users;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.google.common.base.Optional;

import handa.beans.dto.LdapUser;
import handa.beans.dto.LdapUserSearch;

public class LdapSearchUserRestClient
{
    private WebTarget wsTarget;

    public LdapSearchUserRestClient(Client jerseyClient, String ldapSearchUserWsUrl)
    {
        checkNotNull(jerseyClient, "jerseyClient should not be null");
        checkNotNull(ldapSearchUserWsUrl, "ldapSearchUserWsUrl should not be null");
        this.wsTarget = jerseyClient.target(ldapSearchUserWsUrl);
    }

    @SuppressWarnings("rawtypes")
    public Optional<LdapUser> search(LdapUserSearch search)
    {
        checkNotNull(search, "search object should not be null");
        checkNotNull(emptyToNull(search.getUsername()), "username should not be null");
        checkNotNull(emptyToNull(search.getDomain()), "domain should not be null");
        Response response = wsTarget.request().header("Content-Type", "application/json")
                                    .post(Entity.json(search));
        int status = response.getStatus();
        if(status == 200)
        {
            LdapUser user = new LdapUser();
            Map map = response.readEntity(Map.class);
            user.setCompany(search.getDomain());
            user.setDepartment(((String) map.get("department")));
            user.setEmployeeNumber(getEmployeeNumber(map));
            user.setFirstName(((String) map.get("givenName")));
            user.setLastName(((String) map.get("sn")));
            user.setMiddleName(((String) map.get("initials")));
            user.setPosition(((String) map.get("title")));
            user.setAdUsername(search.getUsername().toLowerCase());
            user.setImmediateHead(extractName((String)map.get("manager")));
            return of(user);
        }
        else if(status == 404)
        {
            return absent();
        }
        else
        {
            final String errMsg = String.format("Search via ldap web service failed! Response was code: %s, response string: %s",
                                                 response.getStatus(), response.readEntity(String.class));
            throw new RuntimeException(errMsg);
        }
    }

    // CN=SERMONIA\, Joseph S.,OU=PLDT Executives,OU=PLDT Users,DC=pldt,DC=pldtgroup,DC=net ---> SERMONIA, Joseph S.
    private String extractName(String input)
    {
        if(input == null) return null;
        return input.replaceAll("CN=|\\\\|,OU.*", "");
    }

    @SuppressWarnings("rawtypes")
    private String getEmployeeNumber(Map map)
    {
        String empNo = (String) map.get("employeeNumber");
        return empNo == null? (String) map.get("employeeID") : empNo;
    }
}