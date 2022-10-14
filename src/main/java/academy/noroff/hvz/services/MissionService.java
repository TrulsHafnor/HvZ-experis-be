package academy.noroff.hvz.services;

import academy.noroff.hvz.enums.MissionVisibility;
import academy.noroff.hvz.exeptions.MissionNotFoundException;
import academy.noroff.hvz.models.Mission;
import academy.noroff.hvz.models.Player;
import academy.noroff.hvz.repositories.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MissionService {
    private final MissionRepository missionRepository;
    private final PlayerService playerService;


    @Autowired
    public MissionService(MissionRepository missionRepository, PlayerService playerService) {
        this.missionRepository = missionRepository;
        this.playerService = playerService;
    }

    /**
     * Returns a List of missions
     * @return
     */
    public Set<Mission> findAllMissionsInGame(int gameId, int playerId) {
        Player tempPlayer = playerService.findPlayerById(playerId);
        if (tempPlayer.isHuman()) {
            return missionRepository.getVisibilityOfMission(gameId, MissionVisibility.ZOMBIE.toString());
        }
        return missionRepository.getVisibilityOfMission(gameId, MissionVisibility.HUMAN.toString());
    }

    /**
     * Takes an ID of type int and returns mission with same id.
     * Throws error if not
     * @param missionId
     * @return
     */
    public Mission findMissionById (int missionId) {
        return missionRepository.findById(missionId).orElseThrow(
                () -> new MissionNotFoundException("Mission by id "+ missionId + " was not found"));
    }

    /**
     * Creates a new mission
     * Should be admin only
     * @param mission
     * @return
     */
    public Mission addMission (Mission mission) {
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
        return missionRepository.save(mission);
    }

    /**
     * Deletes game
     * Should only be accessed by Admin
     * @param missionId
     */
    public void deleteMission(int missionId, int game_id) {
        missionRepository.deleteById(missionId);
    }


    /**
     * Get mission in game by id
     * @param game_id
     * @param mission_id
     * @return
     */
    public Mission getMissionInGame(int game_id, int mission_id) {
        return missionRepository.getMissionInGame(game_id, mission_id).orElseThrow(
                () -> new MissionNotFoundException("Cant find mission by id "+ mission_id + " ing game " + game_id));
    }

    /**
     * Delete all missions in game by game_id
     * @param gameId
     */
    public void deleteAllMissionsInGame (int gameId) {
        missionRepository.deleteAllMissionInGame(gameId);
    }

    /**
     * Method to check type of mission, human or zombie.
     * @param missionVisibility
     * @param player_id
     * @param game_id
     * @return
     */
    public Boolean checkMissionType(MissionVisibility missionVisibility, int player_id, int game_id) {
        if(missionVisibility == MissionVisibility.GLOBAL){
            return true;
        }
        if(playerService.findPlayerById(player_id).isHuman() && missionVisibility == MissionVisibility.HUMAN) {
            return true;
        }
        return !playerService.findPlayerById(player_id).isHuman() && missionVisibility == MissionVisibility.ZOMBIE;
    }
}
