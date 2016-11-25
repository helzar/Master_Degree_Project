from MySQL.generator.common import remove_all_data, populate_station_types, create_tables, drop_tables
from MySQL.generator.models import db, ElectricalStation, Transformer, PowerConsumption, StationType, STATION_TYPES


consumption_log_id = 0
transformer_log_id = 0


def get_consumption_log_id():
    global consumption_log_id
    consumption_log_id += 1
    return consumption_log_id


def get_transformer_log_id():
    global transformer_log_id
    transformer_log_id += 1
    return transformer_log_id


def populate_transformers_and_power_consumption_for_station(station, transformers_locations):
    transformer_1 = Transformer.create(id=get_transformer_log_id(), parent_electrical_station=station, region=station.region, location=transformers_locations[0])
    transformer_2 = Transformer.create(id=get_transformer_log_id(), parent_electrical_station=station, region=station.region, location=transformers_locations[1])
    transformer_3 = Transformer.create(id=get_transformer_log_id(), parent_electrical_station=station, region=station.region, location=transformers_locations[2])

    transformer_2_1 = Transformer.create(id=get_transformer_log_id(), parent_transformer=transformer_2, region=station.region, location=transformers_locations[3])
    transformer_2_2 = Transformer.create(id=get_transformer_log_id(), parent_transformer=transformer_2, region=station.region, location=transformers_locations[4])

    transformer_3_1 = Transformer.create(id=get_transformer_log_id(), parent_transformer=transformer_3, region=station.region, location=transformers_locations[5])
    transformer_3_2 = Transformer.create(id=get_transformer_log_id(), parent_transformer=transformer_3, region=station.region, location=transformers_locations[6])
    transformer_3_3 = Transformer.create(id=get_transformer_log_id(), parent_transformer=transformer_3, region=station.region, location=transformers_locations[7])

    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_1, timestamp="10-10-2016T14:10:00", losses=0.01, electric_power=22000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_1, timestamp="10-10-2016T15:10:00", losses=0.01, electric_power=26000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_1, timestamp="10-10-2016T16:10:00", losses=0.01, electric_power=28000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_1, timestamp="10-10-2016T17:10:00", losses=0.02, electric_power=35000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_1, timestamp="10-10-2016T18:10:00", losses=0.03, electric_power=40000, was_enabled=True)

    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_2, timestamp="10-10-2016T14:10:00", losses=0.1, electric_power=20000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_2, timestamp="10-10-2016T15:10:00", losses=0.1, electric_power=22000, was_enabled=True)

    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3, timestamp="10-10-2016T14:10:00", losses=0.01, electric_power=20000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3, timestamp="10-10-2016T15:10:00", losses=0.02, electric_power=31000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3, timestamp="10-10-2016T16:10:00", losses=0.01, electric_power=21000, was_enabled=True)

    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3_1, timestamp="10-10-2016T13:10:00", losses=0.01, electric_power=21000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3_1, timestamp="10-10-2016T14:10:00", losses=0.01, electric_power=29000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3_2, timestamp="10-10-2016T13:10:00", losses=0.01, electric_power=28000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3_2, timestamp="10-10-2016T14:10:00", losses=0.01, electric_power=26000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3_3, timestamp="10-10-2016T13:10:00", losses=0.01, electric_power=24000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_3_3, timestamp="10-10-2016T14:10:00", losses=0.01, electric_power=21000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_2_1, timestamp="10-10-2016T13:10:00", losses=0.01, electric_power=24000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_2_1, timestamp="10-10-2016T14:10:00", losses=0.01, electric_power=26000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_2_2, timestamp="10-10-2016T13:10:00", losses=0.01, electric_power=24000, was_enabled=True)
    PowerConsumption.create(id=get_consumption_log_id(), transformer=transformer_2_2, timestamp="10-10-2016T14:10:00", losses=0.01, electric_power=24000, was_enabled=True)


if __name__ == '__main__':
    db.connect()
    drop_tables()
    create_tables()
    stations = populate_station_types()  # dict of <station_name, station_model_object>

    station_1 = ElectricalStation.create(id=1, type=stations['TERMAL'], is_active=True, name="Добротвірська ТЕС", region="Львівська область", location="Добротвір")
    station_2 = ElectricalStation.create(id=2, type=stations['TERMAL'], is_active=True, name="Запорізька ТЕС", region="Запорізька область", location="Енергодар")
    station_3 = ElectricalStation.create(id=3, type=stations['NUCLEAR'], is_active=True, name="Рівненська АЕС", region="Рівненська область", location="Вараш")

    transformers_locations_for_station_1 = ["Львів", "Стрий", "Рудки", "Жовква", "Червоноград", "Самбір", "Золочів", "Городок"]
    transformers_locations_for_station_2 = ["Мелітополь", "Запоріжжя", "Розівка", "Веселе", "Оріхів", "Якимівка", "Кушугум", "Пологи"]
    transformers_locations_for_station_3 = ["Рівне", "Дубно", "Сарни", "Клесів", "Клевань", "Зарічне", "Корець", "Корост"]

    populate_transformers_and_power_consumption_for_station(station_1, transformers_locations_for_station_1)
    populate_transformers_and_power_consumption_for_station(station_2, transformers_locations_for_station_2)
    populate_transformers_and_power_consumption_for_station(station_3, transformers_locations_for_station_3)

