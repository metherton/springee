package rewards.rest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriUtils;

import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.RewardNetwork;

import common.datetime.SimpleDate;
import common.money.MonetaryAmount;

@Controller
public class DiningController {
	
	@Autowired
	private RewardNetwork rewardNetwork;
	
	private JdbcTemplate jdbcTemplate;  // query directly from controller
	
	@Autowired
	public void initJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@RequestMapping(value="/dining/{txId}", method=POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void reward(@RequestBody rewards.oxm.Dining diningRequest, @PathVariable String txId, 
					   HttpServletRequest request, HttpServletResponse response) {
		if (rewardExists(txId)) {
			throw new DiningAlreadyProcessedException();
		}
		Dining dining = createDining(diningRequest);
		RewardConfirmation confirmation = rewardNetwork.rewardAccountFor(dining, txId);
		
		String rewardPathInfo = "/rewards/" + confirmation.getConfirmationNumber();
		response.addHeader("Location", determineLocationUri(request, rewardPathInfo));
	}

	String determineLocationUri(HttpServletRequest request,	String newPathInfo) {
		String encodedPath;
		try {
			encodedPath = UriUtils.encodePath(newPathInfo, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// Should never happen
			throw new RuntimeException(e);
		}
		StringBuffer url = request.getRequestURL();
		url.replace(url.indexOf(request.getPathInfo()), url.length(), encodedPath);
		return url.toString();
	}

	private boolean rewardExists(String txId) {
		int count = jdbcTemplate.queryForInt("select count(*) from T_REWARD where TRANSACTION_ID = ?", txId);
		return count == 1;
	}

	private Dining createDining(rewards.oxm.Dining diningRequest) {
		return new Dining(new MonetaryAmount(diningRequest.getAmount().getValue()),
					diningRequest.getCreditcard().getNumber(),
					diningRequest.getMerchant().getNumber(),
					SimpleDate.valueOf(diningRequest.getTimestamp().getDate().toGregorianCalendar().getTime())
		);
	}
}
