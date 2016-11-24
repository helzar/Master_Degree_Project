from peewee import *


db = MySQLDatabase('master_project', user="root")

STATION_TYPES = {
    'TERMAL': 1,
    'NUCLEAR': 2,
    'HYDRO': 3,
    'SOLAR': 4,
    'WIND': 5,
}


class StationType(Model):
    id = IntegerField(primary_key=True, unique=True)
    name = CharField(max_length=50)

    class Meta:
        database = db
        db_table = "station_type"


class ElectricalStation(Model):
    id = IntegerField(primary_key=True, unique=True)
    type = ForeignKeyField(StationType, related_name='stations')
    is_active = BooleanField()
    name = CharField(unique=True, max_length=50)
    region = CharField(max_length=50)
    location = CharField(max_length=100)
    description = CharField(max_length=255)

    class Meta:
        database = db
        db_table = "electrical_station"


class Transformer(Model):
    id = IntegerField(primary_key=True, unique=True)
    parent_transformer = ForeignKeyField('self', related_name='child_transformers')
    parent_electrical_station = ForeignKeyField(ElectricalStation, related_name='child_transformers')
    region = CharField(max_length=50)
    location = CharField(max_length=100)
    description = CharField(max_length=255)

    class Meta:
        database = db
        db_table = "transformer"


class PowerConsumption(Model):
    id = IntegerField(primary_key=True, unique=True)
    transformer = ForeignKeyField(Transformer, related_name='consumption_logs')
    timestamp = FixedCharField(max_length=20)
    losses = DoubleField()
    electric_power = IntegerField()
    was_enabled = BooleanField()

    class Meta:
        database = db
        db_table = "power_consumption"
