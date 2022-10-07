package academy.noroff.hvz.services;

import academy.noroff.hvz.enums.MissionVisibility;
import academy.noroff.hvz.exeptions.MissionNotFoundException;
import academy.noroff.hvz.models.Mission;
import academy.noroff.hvz.repositories.GameRepository;
import academy.noroff.hvz.repositories.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MissionService {
    private final MissionRepository missionRepository;
    private final GameRepository gameRepository;

    @Autowired
    public MissionService(MissionRepository missionRepository, GameRepository gameRepository) {
        this.missionRepository = missionRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Returns a List of missions
     * @return
     */
    public Set<Mission> findAllMissions(int gameId, MissionVisibility faction) {
        return missionRepository.getVisibilityOfMission(gameId, faction);
    }

    /**
     * Takes an ID of type int and returns mission with same id.
     * Throws error if not
     * @param missionId
     * @return
     */
    public Mission findMissionById (int gameId, int missionId) {
        Mission mission = missionRepository.getMissionInGame(gameId, missionId);
        return mission;
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
     * @param missionId
     */
    public void deleteMission(int missionId) {
        // TODO: 10/7/2022 ADMIN ONLY
        missionRepository.deleteById(missionId);
    }
}
