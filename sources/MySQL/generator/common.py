from MySQL.generator.models import Transformer, ElectricalStation, PowerConsumption, StationType, STATION_TYPES


def remove_all_data():
    PowerConsumption.delete().execute()
    Transformer.delete().where(~(Transformer.parent_transformer >> None)).execute()
    Transformer.delete().execute()
    ElectricalStation.delete().execute()
    StationType.delete().execute()


def populate_station_types():
    dict = {}
    for name, id in STATION_TYPES.items():
        dict[name] = StationType.create(id=id, name=name)
    return dict

