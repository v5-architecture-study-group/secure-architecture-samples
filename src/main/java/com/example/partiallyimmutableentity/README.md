# Partially Immutable Entities

Entities are by design mutable, as opposed to value objects which are immutable. However, not all attributes of
an entity need to be mutable and these should be made immutable to prevent accidental changes.

In this example, an `Invoice` is always created for a particular `Order`. Once the invoice has been created, the order
cannot be changed. If a mistake was made, the invoice should be deleted and a new one created for the correct order.

Furthermore, an invoice has an internal database ID and an external invoice number. Both of these are used to identify
the invoice and should therefore never change. Thus, the order, ID and number attributes can be made `final`.