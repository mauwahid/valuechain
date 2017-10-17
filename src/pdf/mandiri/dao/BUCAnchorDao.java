package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.BUCAnchor;
import pdf.mandiri.domain.Kanwil;

public interface BUCAnchorDao extends Dao<BUCAnchor> {

	public List<BUCAnchor> getByJoinDF();
	public List<BUCAnchor> getAllAsc();
	public List<String> getAllStringKanwil(Kanwil kanwil);
	
		
}
