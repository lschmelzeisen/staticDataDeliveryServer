package de.metalcon.sdd.action;

import java.util.Queue;

import de.metalcon.sdd.Sdd;
import de.metalcon.sdd.exception.SddException;

public class UpdateOutputAction extends Action {

    private long nodeId;

    public UpdateOutputAction(
            long nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public void runAction(Sdd sdd, Queue<Action> actions) throws SddException {
        sdd.actionUpdateOutput(actions, nodeId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        UpdateOutputAction o = (UpdateOutputAction) other;
        return nodeId == o.nodeId;
    }

    @Override
    public int hashCode() {
        int hash = 66494;
        int mult = 877;

        hash = hash * mult + ((Long) nodeId).hashCode();

        return hash;
    }

}
