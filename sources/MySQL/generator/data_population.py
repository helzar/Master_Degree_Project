from sources.MySQL.generator.common import remove_all_data
from sources.MySQL.generator.models import db, ElectricalStation, Transformer, PowerConsumption


consumption_log_id = 0


def get_consumption_log_id():
    global consumption_log_id
    consumption_log_id += 1
    return consumption_log_id


if __name__ == '__main__':
    db.connect()
    remove_all_data()

    station_1 = ElectricalStation.create(id=1, is_active=False, name="Second странція")

    transformer_1 = Transformer.create(id=1, region="Львівська область", parent_electrical_station=station_1)
    transformer_2 = Transformer.create(id=2, region="Львівська область", parent_electrical_station=station_1)
    transformer_3 = Transformer.create(id=3, region="Львівська область", parent_electrical_station=station_1)

    transformer_3_1 = Transformer.create(id=4, region="Львівська область", parent_transformer=transformer_3)

    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_1, timestamp="10.11.2016 14:10:00", losses=0.01, electric_power=22000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_1, timestamp="10.11.2016 15:10:00", losses=0.01, electric_power=26000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_1, timestamp="10.11.2016 16:10:00", losses=0.01, electric_power=28000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_1, timestamp="10.11.2016 17:10:00", losses=0.02, electric_power=35000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_1, timestamp="10.11.2016 18:10:00", losses=0.03, electric_power=40000, was_enabled=True)

    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_2, timestamp="10.11.2016 14:10:00", losses=0.1, electric_power=20000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_2, timestamp="10.11.2016 15:10:00", losses=0.1, electric_power=20000, was_enabled=True)

    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3, timestamp="10.11.2016 14:10:00", losses=0.01, electric_power=20000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3, timestamp="10.11.2016 15:10:00", losses=0.02, electric_power=30000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3, timestamp="10.11.2016 16:10:00", losses=0.01, electric_power=21000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3_1, timestamp="10.11.2016 13:10:00", losses=0.01, electric_power=24000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3_1, timestamp="10.11.2016 16:10:00", losses=0.01, electric_power=26000, was_enabled=True)

