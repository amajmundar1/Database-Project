CREATE INDEX onHandNYC
ON part_nyc(on_hand);

CREATE INDEX ColorNYC
ON part_nyc(color);

CREATE INDEX SupplierNYC
ON part_nyc(supplier);

CREATE INDEX SupplierSFO
ON part_sfo(supplier);

CREATE INDEX SuppliersName
ON supplier(supplier_name);
