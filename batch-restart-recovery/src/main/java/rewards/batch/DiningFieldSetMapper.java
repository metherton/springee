package rewards.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import rewards.Dining;

public class DiningFieldSetMapper implements FieldSetMapper<Dining> {

	//	TODO 03a: Implement this method, it should create and return a Dining object based on fieldSet contents.
	//	Note the field names can be determined by examining the diningRequestsReader bean definition from the last step.
	//	Also note the Dining object has a convenient createDining() method.
	@Override
	public Dining mapFieldSet(FieldSet fieldSet) throws BindException {
		return null;	//	Replace with your implementation
	}

}
