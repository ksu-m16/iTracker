/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.collect.fsm;

/**
 *
 * @author KerneL
 */
public class FsmController {
    
    public enum Input {
        STOP(0), START(1), POWER_OFF(2), POWER_ON(3), KIT_OFF(4), KIT_ON(5);
        
        Input(int idx) {
            index = idx;
        }
        
        final int index;
        int getIndex() {
            return index;
        }
    }
    
    public enum State {
        IDLE(0), IDLE_P(1), IDLE_K(2), IDLE_KP(3), 
        RUN(4), RUN_P(5), RUN_K(6), RUN_KP(7), ERROR(8);
        
        int index;
        State(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }                
        
        public static final State[][] table = {
            {ERROR,   RUN,    ERROR,  RUN_P,  ERROR,  RUN_K  },
            {ERROR,   RUN_P,  IDLE,   ERROR,  ERROR,  RUN_KP },
            {ERROR,   RUN_K,  ERROR,  RUN_KP, IDLE,   ERROR  },
            {ERROR,   RUN_KP, IDLE_K, ERROR,  IDLE_P, ERROR  },
            {IDLE,    ERROR,  ERROR,  RUN_P,  ERROR,  RUN_K  },
            {IDLE_P,  ERROR,  RUN,    ERROR,  ERROR,  RUN_KP },
            {IDLE_K,  ERROR,  ERROR,  RUN_KP, RUN,    ERROR  },
            {IDLE_KP, ERROR,  RUN_K,  ERROR,  RUN_P,  ERROR  }                
        };            
    }
        
    private State state = State.IDLE;

    public void reset() {
        state = State.IDLE;
    }    
    
    public State getState() {
        return state;
    }
        
    public State update(Input i) {
        if (state == State.ERROR) {
            return State.ERROR;
        }
        state = State.table[state.getIndex()][i.getIndex()];
        return state;
    }
}
