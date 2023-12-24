import { lazy } from 'react';
import { Outlet, Route, Routes } from 'react-router-dom';

const List = lazy(() => import('./List'));
const Create = lazy(() => import('./Create'));
const Detail = lazy(() => import('./Detail'));

export default function Apps() {
	return (
		<Routes>
			<Route path="/" element={<Outlet />}>
				<Route path="list" element={<List />} />
				<Route path="create" element={<Create />} />
				<Route path="detail/:noticeId" element={<Detail />} />
			</Route>
		</Routes>
	);
}
