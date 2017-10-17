package pdf.mandiri.dao;

import java.util.List;

import pdf.mandiri.domain.Anchor;
import pdf.mandiri.domain.Kanwil;

public interface AnchorDao extends Dao<Anchor>{

	public List<Anchor> getByJoinDF();
	public List<String> getAllString();
	public List<Anchor> getAllAsc();
	public List<String> getAllStringKanwil(Kanwil kanwil);
}

