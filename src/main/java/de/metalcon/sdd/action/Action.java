package de.metalcon.sdd.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.metalcon.sdd.Sdd;
import de.metalcon.sdd.exception.SddException;

public abstract class Action implements Comparable<Action> {

    private static final Map<Class<? extends Action>, Integer> classPriority;
    static {
        Map<Class<? extends Action>, Integer> p =
                new HashMap<Class<? extends Action>, Integer>();
        // When extendening Action, add new classes here:
        p.put(AddRelationsAction.class, 1);
        p.put(DeleteAction.class, 1);
        p.put(DeleteRelationsAction.class, 1);
        p.put(SetPropertiesAction.class, 1);
        p.put(SetRelationAction.class, 1);
        p.put(SetRelationsAction.class, 1);
        p.put(UpdateOutputAction.class, 2);
        p.put(UpdateReferencingAction.class, 3);
        classPriority = Collections.unmodifiableMap(p);
    }

    // TODO: is int a good choice for this?
    private static int insertCounter = 0;

    private int insertCount;

    public Action() {
        insertCount = ++insertCounter;
    }

    public abstract void runAction(Sdd sdd) throws SddException;

    @Override
    public int compareTo(Action other) {
        if (other == null) {
            return -1;
        }

        int thisPriority = classPriority.get(getClass());
        int otherPriority = classPriority.get(other.getClass());

        if (thisPriority == otherPriority) {
            return (insertCount - other.insertCount) < 0 ? -1 : 1;
        } else if (thisPriority > otherPriority) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract int hashCode();

}
