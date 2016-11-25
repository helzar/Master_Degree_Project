from MySQL.generator.models import Transformer, ElectricalStation, PowerConsumption, StationType, STATION_TYPES


def remove_all_data():
    PowerConsumption.delete().execute()
    Transformer.delete().where(~(Transformer.parent_transformer >> None)).execute()
    Transformer.delete().execute()
    ElectricalStation.delete().execute()
    StationType.delete().execute()


def drop_tables():
    if PowerConsumption.table_exists():
        PowerConsumption.drop_table()
    if Transformer.table_exists():
        Transformer.drop_table()
    if ElectricalStation.table_exists():
        ElectricalStation.drop_table()
    if StationType.table_exists():
        StationType.drop_table()


def create_tables():
    StationType.create_table()
    ElectricalStation.create_table()
    Transformer.create_table()
    PowerConsumption.create_table()


def populate_station_types():
    dict = {}
    for name, id in STATION_TYPES.items():
        dict[name] = StationType.create(id=id, name=name)
    return dict

