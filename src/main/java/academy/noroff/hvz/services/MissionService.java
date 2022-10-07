package academy.noroff.hvz.services;

import academy.noroff.hvz.exeptions.GameNotFoundException;
import academy.noroff.hvz.exeptions.MissionNotFoundException;
import academy.noroff.hvz.models.Game;
import academy.noroff.hvz.models.Mission;
import academy.noroff.hvz.repositories.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionService {
    private final MissionRepository missionRepository;

    @Autowired
    public MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    /**
     * Returns a List of missions
     * @return
     */
    public List<Mission> findAllMissions() {
        return missionRepository.findAll();
    }

    /**
     * Takes an ID of type int and returns mission with same id.
     * Throws error if not
     * @param id
     * @return
     */
    public Mission findMissionById (int id) {
        return missionRepository.findById(id). orElseThrow(
                () -> new MissionNotFoundException("Mission by id "+ id + " was not found"));
    }

    /**
     * Creates a new mission
     * Should be admin only
     * @param mission
     * @return
     */
    public Mission addMission (Mission mission) {
        // TODO: 10/7/2022 ADMIN ONLY
        return missionRepository.save(mission);
    }

    /**
     * Updates game
     * Should only be accessed by Admin
     * @param mission
     * @return
     */
    public Mission updateMission (Mission mission) {
        // TODO: 10/5/2022 Sjekk om denne er riktig når vi har aktivt mission
        // TODO: 10/7/2022 ADMIN ONLY
        return missionRepository.save(mission);
    }

    /**
     * Deletes game
     * Should only be accessed by Admin
     * @param id
     */
    public void deleteMission(int id) {
        // TODO: 10/7/2022 ADMIN ONLY
        missionRepository.deleteById(id);
    }


}
