from sources.MySQL.generator.models import Transformer, ElectricalStation, PowerConsumption


def remove_all_data():
    PowerConsumption.delete().execute()
    Transformer.delete().where(~(Transformer.parent_transformer >> None)).execute()
    Transformer.delete().execute()
    ElectricalStation.delete().execute()

