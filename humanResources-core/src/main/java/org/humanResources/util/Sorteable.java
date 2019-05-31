package org.humanResources.util;

/**
 ***************************************************************************************************
 * Simple interface for entities that may be assigned a sort order; this field may be left null, in
 * which case the comparator is left to decide whether this is permissible, whether entities with a
 * null sort order should come first/last, and how entities with null sort order should be sorted
 * among themselves
 ***************************************************************************************************
 */
public interface Sorteable
{
	Integer getSortOrder();
	void setSortOrder(Integer i);
}
