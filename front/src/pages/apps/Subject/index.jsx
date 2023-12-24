import { lazy } from 'react';
import { Outlet, Route, Routes } from 'react-router-dom';

const List = lazy(() => import('./List'));
const Create = lazy(() => import('./Create'));
const Join = lazy(() => import('./Join'));

export default function Dashboard() {
	return (
		<Routes>
			<Route path="/*" element={<Outlet />}>
				<Route path="list" element={<List />} />
				<Route path="create" element={<Create />} />
				<Route path="input-code" element={<Join />} />
			</Route>
		</Routes>
	);
}
