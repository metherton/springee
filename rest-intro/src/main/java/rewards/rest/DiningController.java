package rewards.rest;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
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

	// TODO 01: Annotate the method with @RequestMapping, configured to be invoked for POSTs to /dining/{txId}
	
	// TODO 02: Annotate the diningRequest param with @RequestBody and the txId param with @PathVariable 
	
	// TODO 05: Annotate the method with @ResponseStatus to return 201 Created
	public void reward(rewards.oxm.Dining diningRequest, String txId, 
					   HttpServletRequest request, HttpServletResponse response) {
		if (rewardExists(txId)) {
			throw new DiningAlreadyProcessedException();
		}
		Dining dining = createDining(diningRequest);
		RewardConfirmation confirmation = rewardNetwork.rewardAccountFor(dining, txId);
		
		String rewardPathInfo = "/rewards/" + confirmation.getConfirmationNumber();
	
	/* TODO 04: Add a Location header to the response with the absolute URI of the new reward,
		 		using the determineLocationUri helper method to determine the URI based on the current request URI */
		
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
