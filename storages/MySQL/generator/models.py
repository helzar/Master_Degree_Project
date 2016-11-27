import datetime
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
    id = PrimaryKeyField()
    name = CharField(max_length=50, null=False)

    class Meta:
        database = db
        db_table = "station_type"


class ElectricalStation(Model):
    id = PrimaryKeyField()
    type = ForeignKeyField(StationType, related_name='stations', null=False)
    is_active = BooleanField(null=False)
    name = CharField(unique=True, max_length=50, null=False)
    region = CharField(max_length=50, null=True)
    location = CharField(max_length=100, null=True)
    description = CharField(max_length=255, null=True)

    class Meta:
        database = db
        db_table = "electrical_station"


class Transformer(Model):
    id = PrimaryKeyField()
    parent_transformer = ForeignKeyField('self', related_name='child_transformers', null=True)
    parent_electrical_station = ForeignKeyField(ElectricalStation, related_name='child_transformers', null=True)
    region = CharField(max_length=50, null=True)
    location = CharField(max_length=100, null=True)
    description = CharField(max_length=255, null=True)

    class Meta:
        database = db
        db_table = "transformer"


class PowerConsumption(Model):
    id = PrimaryKeyField()
    transformer = ForeignKeyField(Transformer, related_name='consumption_logs', null=False)
    generation_timestamp = FixedCharField(max_length=20, null=False)
    losses = DoubleField(null=True)
    electric_power = IntegerField(null=False)
    was_enabled = BooleanField(null=True)
    insertion_timestamp = FixedCharField(max_length=20, null=False)

    def save(self, *args, **kwargs):
        self.insertion_timestamp = datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S")
        return super(PowerConsumption, self).save(*args, **kwargs)

    class Meta:
        database = db
        db_table = "power_consumption"
