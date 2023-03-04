import {AddTrack} from '@components/AddTrack';
import {Page} from '@interfaces/page';
import {TrackList} from '@components/TrackList';
import {useTitle} from '@hooks/useTitle';
import {useTracks} from "@hooks/useTracks";

export interface TracksProps extends Page {
}

export function Tracks({title}: TracksProps) {
  useTitle(title);
  const {loading, error, tracks, addTrack} = useTracks();

  return (
    <>
      <TrackList loading={loading} error={error} tracks={tracks}/>
      <AddTrack addTrack={addTrack} />
    </>
  );
}
