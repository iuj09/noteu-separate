import { lazy } from 'react';
import { Outlet, Route, Routes } from 'react-router-dom';

const List = lazy(() => import('./List'));
const Detail = lazy(() => import('./Detail'));
const Create = lazy(() => import('./Create'));
const Update = lazy(() => import('./Update'))

export default function Apps() {
	return (
		<Routes>
			<Route path="/" element={<Outlet />}>
				<Route path="list" element={<List />} />
				<Route path="detail/:referenceRoomId" element={<Detail />} />
				<Route path="create" element={<Create />} />
				<Route path="update/:referenceRoomId" element={<Update />} />
			</Route>
		</Routes>
	);
}
