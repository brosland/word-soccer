package fi.jamk.wordsoccer.database;

public interface IModel
{
	public interface IDatabaseColumn
	{
		public String getName();

		public String getType();

		public String getAttributes();
	}

	public String getTableName();

	public IDatabaseColumn[] getColumns();
}