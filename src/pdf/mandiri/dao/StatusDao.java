package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.Kanwil;
import pdf.mandiri.domain.Status;

public interface StatusDao extends Dao<Status> {
	public List<Status> getAllAsc();
	public List<String> getAllStringKanwil(Kanwil kanwil);


}
